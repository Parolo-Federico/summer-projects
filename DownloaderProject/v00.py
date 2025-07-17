import tkinter as tk
from tkinter import ttk
import threading
from tkinter.messagebox import showinfo
from tkinter import filedialog
from ctypes import windll
import yt_dlp
import customtkinter as ctk
from CTkMessagebox import CTkMessagebox
import queue
import re

class App(ctk.CTk):
    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)
        ctk.set_appearance_mode('dark')
        ctk.set_default_color_theme('blue')
        font = ('Helvetica',16)
        self.folder = ''
        self.logger = MyLogger()
        self.title("Video/Audio Downloader")
        screenW = self.winfo_screenwidth()
        screenH = self.winfo_screenheight()
        centerX = int(screenW/2 -250)
        centerY = int(screenH/2 - 170)
        # TODO better centerscreen
        self.geometry(f"500x340+{centerX}+{centerY}")
        #icona
        self.resizable(False,False)
        
        # url frame
        self.urlFrame = ctk.CTkFrame(self,fg_color='transparent')
        self.urlFrame.pack(pady=20,padx=20,anchor='w')
        
        # insert url label
        self.urlFrame.label = ctk.CTkLabel(self.urlFrame,text="Insert the URL:", font=font)
        self.urlFrame.label.pack(side="left", padx=35)
        
        # insert url entry
        self.urlFrame.urlEntry = ctk.CTkEntry(self.urlFrame,width=250,font=font,placeholder_text='https://youtube.com/...')
        self.urlFrame.urlEntry.pack(side='left')
        
        # select folder frame
        self.selFolderFrame = ctk.CTkFrame(self,fg_color='transparent')
        self.selFolderFrame.pack(pady=10)
        
        # select forder button
        self.selFolderFrame.selFolderButton = ctk.CTkButton(self.selFolderFrame,text="Select Folder", corner_radius=20, font=font,command=self.choose_folder)
        self.selFolderFrame.selFolderButton.pack(side='left',padx=10)
        
        # clear folder button
        self.selFolderFrame.clearFolderButton = ctk.CTkButton(self.selFolderFrame,text="Clear folder selection ", corner_radius=20, font=font,command=self.clear_folder)
        self.selFolderFrame.clearFolderButton.pack(side='left',padx=10)

        # folder text area
        self.folderPathArea = ctk.CTkTextbox(self,height=30, width=350,)
        self.folderPathArea.configure(state='disabled',font=font)
        self.folderPathArea.pack(padx=20,pady=20)

        # file format label
        self.formatLabel = ctk.CTkLabel(self,text='File Format:',font=font)
        self.formatLabel.place(relx=0.5,rely=0.6,anchor='center')

        # radio button frame
        self.formatFrame = ctk.CTkFrame(self,fg_color='transparent')
        self.formatFrame.place(relx=0.5,rely=0.7,anchor='center')

        self.fileFormatVar = tk.StringVar(value='opt1')

        #MP3 format radio button
        self.formatFrame.mp3Button = ctk.CTkRadioButton(self.formatFrame,text='MP3 (audio only)',variable=self.fileFormatVar,value='opt1',font=font)
        self.formatFrame.mp3Button.pack(side='left',padx=10)

        #MP4 format radio button
        self.formatFrame.mp4Button = ctk.CTkRadioButton(self.formatFrame,text='MP4 (video & audio)',variable=self.fileFormatVar,value='opt2',font=font)
        self.formatFrame.mp4Button.pack(side='left',padx=10)
        
        # download button TODO command
        self.downloadButton = ctk.CTkButton(self,text='Download',corner_radius=20,font=font,command=self.check_and_download)
        self.downloadButton.place(relx=0.5,rely=0.85,anchor='center')

        #download in progress window (DIP)
        self.DIPwindow = None

    # onEnter and onLeave for buttons TODO fix
    def on_enter(event):
        event.widget.configure(cursor="hand2")  # cambia il cursore in mano

    def on_leave(event):
        event.widget.configure(cursor="")  # torna al cursore di default
    
    def choose_folder(self):
        self.folder = filedialog.askdirectory()
        if self.folder:
            self.folderPathArea.configure(state='normal')
            self.folderPathArea.delete('0.0','end')
            self.folderPathArea.insert('0.0',self.folder)
            self.folderPathArea.configure(state='disabled')
    
    def clear_folder(self):
        self.open_download_window()
        self.folderPathArea.configure(state='normal')
        self.folderPathArea.delete('0.0','end')
        self.folderPathArea.configure(state='disabled')

    def check_and_download(self):
        folder = self.folderPathArea.get('0.0','end').replace('\n','')

        if folder == '':
            self.show_error_messageBox('Select destination folder before downloading.')

        elif self.urlFrame.urlEntry.get() == '':
            self.show_error_messageBox('Insert media URL before downloading.')

        elif re.search('^https://(www\.youtube\.com|youtu\.be)/.*',self.urlFrame.urlEntry.get()) == None :
            self.show_error_messageBox('Insert a valid youtube URL')
            self.urlFrame.urlEntry.delete(0,'end')

        else:
            self.start_download(self.urlFrame.urlEntry.get())

    def show_error_messageBox(self,msg):
        CTkMessagebox(title='Error',message=msg,icon='cancel',option_1='OK').focus()

    # Hook per aggiornare i progressi
    
    def progress_hook(self,d):
        if d['status'] == 'downloading':
            percent = d.get('_percent_str', '').strip()
            speed = d.get('_speed_str', '').strip()
            eta = d.get('_eta_str', '').strip()
            size = d.get('_total_bytes_str', '') or d.get('total_bytes', '')
            
            label_text = f"[download] {percent} of {size} at {speed} ETA {eta}"

            # Aggiorna i Label in modo thread-safe TODO
            self.DIPwindow.progressFrame.percentLabel.configure(text='State: '+percent)
            self.DIPwindow.progressFrame.fileSizeLabel.configure(text='Size:'+size)
            self.DIPwindow.progressFrame.speedLabel.configure(text='Speed: '+speed)
            self.DIPwindow.progressFrame.ETALabel.configure(text='ETA: '+eta)
        elif d['status'] == 'finished':
            self.DIPwindow.label.configure(text='Download completato!')
            self.DIPwindow.destroy()


    def start_download(self,url):
        ydl_opts = {
            'format': 'bestaudio/best',
            'outtmpl': self.folder + '/%(title)s.%(ext)s',
            'progress_hooks': [self.progress_hook],
            'logger' : self.logger,
            'no_color' : True,
            'postprocessors': [{
                'key': 'FFmpegExtractAudio',
                'preferredcodec': 'mp3',
                'preferredquality': '192',
            }],
        }
        ydl_optsVideo = {
            'format': 'bestvideo+bestaudio/best',  # scarica miglior video e audio disponibili
            'outtmpl': self.folder + '/%(title)s.%(ext)s',
            'progress_hooks': [self.progress_hook],
            'no_color' : True,
            'logger' : self.logger,
            'postprocessors': [{
            'key': 'FFmpegVideoConvertor',
            'preferedformat': 'mp4',
            }],
        }

        def download():
            
            if self.fileFormatVar.get() == 'opt2':
                with yt_dlp.YoutubeDL(ydl_optsVideo) as ydl:
                    ydl.download([url])
            elif self.fileFormatVar.get() == 'opt1':
                with yt_dlp.YoutubeDL(ydl_opts) as ydl:
                    ydl.download([url])
            else:
                print('errore impossibile')
        self.open_download_window()
        threading.Thread(target=download).start()
    
    def open_download_window(self):
        if self.DIPwindow == None or not self.DIPwindow.winfo_exists():
            self.DIPwindow = ToplevelWindow(self)
            self.DIPwindow.attributes('-topmost',1)
        else:
            self.DIPwindow.focus()




class ToplevelWindow(ctk.CTkToplevel):
    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)
        screenW = self.winfo_screenwidth()
        screenH = self.winfo_screenheight()
        centerX = int(screenW/2 -250)
        centerY = int(screenH/2 - 50)
        self.geometry(f"500x100+{centerX}+{centerY}")
        self.title('Download in progress')
        font = ('Helvetica',16)
        self.label = ctk.CTkLabel(self, text="Download:",font=font)
        self.label.pack(padx=20, pady=20)
        self.progressFrame = ctk.CTkFrame(self,fg_color='transparent')
        self.progressFrame.pack(side='left',anchor='center')
        self.progressFrame.percentLabel = ctk.CTkLabel(self.progressFrame,text='',font=font)
        self.progressFrame.percentLabel.pack(side='left',padx=10)
        self.progressFrame.fileSizeLabel = ctk.CTkLabel(self.progressFrame,text='',font=font)
        self.progressFrame.fileSizeLabel.pack(side='left',padx=10)
        self.progressFrame.speedLabel = ctk.CTkLabel(self.progressFrame,text='',font=font)
        self.progressFrame.speedLabel.pack(side='left',padx=10)
        self.progressFrame.ETALabel = ctk.CTkLabel(self.progressFrame,text='',font=font)
        self.progressFrame.ETALabel.pack(side='left',padx=10)
        
       
class MyLogger():
    def debug(self, msg):
        #pass
        print(msg)

    def warning(self, msg):
        #pass
        print(msg)

    def error(self, msg):
        #pass
        print(msg)

    def info(self, msg):
        #pass
        print(msg)
    




if __name__ == "__main__":
    windll.shcore.SetProcessDpiAwareness(1)
    app = App()
    app.mainloop()