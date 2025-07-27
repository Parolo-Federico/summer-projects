# version 1.0


import tkinter as tk
import threading
from tkinter import filedialog
from ctypes import windll
import yt_dlp
import customtkinter as ctk
from CTkMessagebox import CTkMessagebox
from PIL import Image
import requests
from io import BytesIO
import re
import json
from pathlib import Path
import base64

SECRET_KEY = "q3X2mB#eRt@P9u"
#ACTIVATION_FILE = "activation.json"
BASE_DIR = Path(__file__).resolve().parent

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
        self.confirm_window = None

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
            url = self.urlFrame.urlEntry.get()
            global info
            info = self.get_video_info(url=url)
            self.show_confirm_window()
            #self.start_download(self.urlFrame.urlEntry.get())


    def get_video_info(self,url):
        ydl_opts = {'quiet': True, 'skip_download': True}
        with yt_dlp.YoutubeDL(ydl_opts) as ydl:
            return ydl.extract_info(url, download=False)
        
    def show_confirm_window(self):
        if self.confirm_window == None or not self.confirm_window.winfo_exists():
            self.confirm_window = ConfirmWindow(self)
            self.confirm_window.attributes('-topmost',1)
            self.confirm_window.focus()
        else:
            self.confirm_window.focus()
        



    def show_error_messageBox(self,msg):
        CTkMessagebox(title='Error',message=msg,icon='cancel',option_1='OK').focus()
    
    def show_download_end(self):
        CTkMessagebox(title='Done',message='Download Finished, find the media in the specified folder.',icon='check',option_1='Close').focus()

    # Hook per aggiornare i progressi
    
    def progress_hook(self,d):
        if d['status'] == 'downloading':
            percent = d.get('_percent_str', '').strip()
            speed = d.get('_speed_str', '').strip()
            eta = d.get('_eta_str', '').strip()
            size = d.get('_total_bytes_str', '') or d.get('total_bytes', '')
            
            #label_text = f"[download] {percent} of {size} at {speed} ETA {eta}"

            # Aggiorna i Label in modo thread-safe TODO
            print('.'+percent+'.')
            self.DIPwindow.progressFrame.percentLabel.configure(text=f'State: {percent}')
            self.DIPwindow.progressFrame.fileSizeLabel.configure(text=f'Size:{size}')
            self.DIPwindow.progressFrame.speedLabel.configure(text=f'Speed: {speed}')
            self.DIPwindow.progressFrame.ETALabel.configure(text=f'ETA: {eta}')
        elif d['status'] == 'finished':
            self.DIPwindow.label.configure(text='Download completato!')
            self.DIPwindow.destroy()
            self.show_download_end()


    def start_download(self):
        self.confirm_window.destroy()
        url = self.urlFrame.urlEntry.get()
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
            self.DIPwindow = DownloadWindow(self)
            self.DIPwindow.attributes('-topmost',1)
        else:
            self.DIPwindow.focus()




class DownloadWindow(ctk.CTkToplevel):
    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)
        screenW = self.winfo_screenwidth()
        screenH = self.winfo_screenheight()
        centerX = int(screenW/2 -250)
        centerY = int(screenH/2 - 50)
        self.geometry(f"500x100+{centerX}+{centerY}")
        self.title('Download in progress')
        font = ('Helvetica',16)
        self.resizable(False,False)
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
        
class ConfirmWindow(ctk.CTkToplevel):
    def __init__(self,*args, **kwargs):
        super().__init__(*args, **kwargs)
        screenW = self.winfo_screenwidth()
        screenH = self.winfo_screenheight()
        centerX = int(screenW/2 -200)
        centerY = int(screenH/2 - 185)
        self.geometry(f"400x370+{centerX}+{centerY}")
        self.title('Confirmation')
        font = ('Helvetica',16)
        self.resizable(False,False)
        global info
        img_data = requests.get(info['thumbnail']).content
        img_pil = Image.open(BytesIO(img_data))
        img_ctk = ctk.CTkImage(light_image=img_pil, size=(320,180))

         # Mostra copertina
        self.thumb = ctk.CTkLabel(self, image=img_ctk, text="",corner_radius=20,)
        self.thumb.pack(pady=5)

        # Mostra dettagli
        title = info['title']
        length = len(title)
        shown_title = ''
        if length >= 30:
            i = 0
            while i < length:
                if i + 30 > length:
                    shown_title += title[i:]
                else:
                    shown_title += title[i:i+30] + "₌\n"
                i += 30
        
        self.title = ctk.CTkLabel(self, text=shown_title, font=font)
        self.title.pack()

        duration = info['duration']
        min = int(duration/60)
        self.duration = ctk.CTkLabel(self, text=f"Duration: s",font=font)
        if(min > 0):
            self.duration.configure(text=f'Duration: {min}min {duration % 60}s')
        else:
            self.duration.configure(text=f'Duration: {duration}s')
        self.duration.pack(pady=5)

        self.uploader = ctk.CTkLabel(self, text=f"Channel: {info['uploader']}",font=font)
        self.uploader.pack(pady=5)

        # Bottone conferma
        self.confirm = ctk.CTkButton(self, text='Confirm',font=('Helvetica',16), command=app.start_download)
        self.confirm.pack(pady=5)

       
class MyLogger():
    def debug(self, msg):
        #pass
        print('debug ' + msg)

    def warning(self, msg):
        #pass
        print('warning ' + msg)

    def error(self, msg):
        #pass
        print('error ' + msg)

    def info(self, msg):
        #pass
        print('info ' + msg)

class ActivationApp(ctk.CTk):
    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)
        screenW = self.winfo_screenwidth()
        screenH = self.winfo_screenheight()
        centerX = int(screenW/2 - 150)
        centerY = int(screenH/2 - 150)
        self.geometry(f"300x200+{centerX}+{centerY}")
        self.title('Activate your product')
        font = ('Helvetica',16)
        self.resizable(False,False)
        ctk.CTkLabel(self, text="Insert product key:", font=font).pack(pady=10)
        self.entry = ctk.CTkEntry(self, width=180,placeholder_text="XXXX-XXXX-XXXX")
        self.entry.pack(pady=10,padx=20)
        self.status = ctk.CTkLabel(self, text="", text_color="red")
        self.status.pack()
        ctk.CTkButton(self, text="Activate", command=self.check_and_register_key).pack(pady=10)

    def check_and_register_key(self):
    # Carica dati
        input_key = self.entry.get().strip()
        keys_status = load_json("keys_status.json")  # es. {"ABC123XYZ": "permanent", "XYZ789ABC": "single-use"}
        used_keys = load_json("used_keys.json")  # es. {"XYZ789ABC": True}

        if input_key not in keys_status:
            self.status.configure(text="Invalid Product Key")
            return False
        
        status = keys_status[input_key]

        if status == "single-use" and input_key in used_keys:
            self.status.configure(text="Product key already used")
            return False,
        
        # Se arriva qui, key valida
        # Registra la key come usata se single-use
        if status == "single-use":
            used_keys[input_key] = True
            save_json("used_keys.json", used_keys)

        save_json("current_key.json", {"product_key": input_key, "status": status})
        self.destroy()
        app=App()
        app.mainloop()

# returns content of a json file, returns {} if file does not exist
def load_json(filename):
    file = BASE_DIR/'license'/filename
    if file.exists():
        with open(file, "r", encoding="utf-8") as f:
            return json.load(f)
    else:
        return {}
    

def save_json(filename, data):
    file = BASE_DIR/'license'/filename
    with open(file, "w", encoding="utf-8") as f:
        json.dumps(data, indent=4)

    
def check_activation():
    current_key = load_json('current_key.json')
    return current_key["status"]

def encrypt_data(data: str) -> str:
    xor_result = ''.join(chr(ord(c) ^ ord(SECRET_KEY[i % len(SECRET_KEY)])) for i, c in enumerate(data))
    encoded = base64.b64encode(xor_result.encode("utf-8")).decode("utf-8")
    return encoded

def decrypt_data(encoded: str) -> str:
    try:
        decoded = base64.b64decode(encoded.encode("utf-8")).decode("utf-8")
        original = ''.join(chr(ord(c) ^ ord(SECRET_KEY[i % len(SECRET_KEY)])) for i, c in enumerate(decoded))
        return original
    except Exception as e:
        print("Errore nella decriptazione:", e)
        return ""


if __name__ == "__main__":
    windll.shcore.SetProcessDpiAwareness(1)
    app = App()
    if check_activation() and check_activation() != "single-use" or check_activation() == 'permanent':
        app.mainloop()
    else:
        activation_app = ActivationApp()
        activation_app.attributes("-topmost",1)
        activation_app.focus()
        activation_app.mainloop()

