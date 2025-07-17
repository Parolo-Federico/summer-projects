import tkinter as tk
from tkinter import ttk
import threading
from tkinter.messagebox import showinfo
from tkinter import filedialog
from ctypes import windll
import yt_dlp
import customtkinter as ctk

# aggiungi barra progresso download  e un gif carifamento per il postprocessing del file 
# e conferma, link non validi gestisci e migliora in generale grafica
   
def choose_folder():
    folder = filedialog.askdirectory()
    if folder != folderPathArea.get('0.0',tk.END):
        folderPathArea.configure(state='normal')
        folderPathArea.delete('0.0','end')
        folderPathArea.insert('0.0',folder)
        folderPathArea.configure(state='disabled')

def clear_folder():
        folderPathArea.configure(state='normal')
        folderPathArea.delete('0.0','end')
        folderPathArea.configure(state='disabled')

def checkAndDownload():
    if folderPathArea.get('0.0','end') and urlEntry.get() :
        root.topLevel = ctk.CTkToplevel(root,mode='indeterminate',fg_color='blue',corner_radius=20,bg_color='transparent')
        start_download(folder=folderPathArea.get('0.0','end'),url=urlEntry.get())

def on_enter(event):
    event.widget.configure(cursor="hand2")  # cambia il cursore in mano

def on_leave(event):
    event.widget.configure(cursor="")  # torna al cursore di default

# Hook per aggiornare la progress bar
def progress_hook(d):
    if d['status'] == 'downloading':
        # Percentuale tra 0 e 1
        percent = d.get('_percent_str', '0%').strip().replace('%', '')
        try:
            percent_float = float(percent) / 100
            root.progressBar.set(percent_float)
        except:
            pass
    elif d['status'] == 'finished':
        root.progressbar.set(1.0)

# Funzione per avviare il download in un thread separato
def start_download(folder,url):

    ydl_opts = {
        'format': 'bestaudio/best',
        'outtmpl': folder + '/%(title)s.%(ext)s',
        'progress_hooks': [progress_hook],
        'postprocessors': [{
            'key': 'FFmpegExtractAudio',
            'preferredcodec': 'mp3',
            'preferredquality': '192',
        }],
    }
    ydl_optsVideo = {
    'format': 'bestvideo+bestaudio/best',  # scarica miglior video e audio disponibili
    'outtmpl': folder + '/%(title)s.%(ext)s',
    'progress_hooks': [progress_hook],
    'postprocessors': [{
    'key': 'FFmpegVideoConvertor',
    'preferedformat': 'mp4',
    }],

    }

    def download():
        if radio_var.get() == 'Option1':
            with yt_dlp.YoutubeDL(ydl_optsVideo) as ydl:
                ydl.download([url])
        else:
            with yt_dlp.YoutubeDL(ydl_opts) as ydl:
                ydl.download([url])

    threading.Thread(target=download).start()

ctk.set_appearance_mode("dark")
ctk.set_default_color_theme("blue")
font=('Helvetica',16)
root = ctk.CTk()
root.title("Video/Audio Downloader")
screenW = root.winfo_screenwidth()
screenH = root.winfo_screenheight()
centerX = int(screenW/2 -250)
centerY = int(screenH/2 - 170)
root.geometry(f"500x340+{centerX}+{centerY}")
#root.iconbitmap("./assets/icona.ico")
root.resizable(False,False)



# Label + Entry frame
frame = ctk.CTkFrame(root, fg_color="transparent")
frame.pack(pady=20, padx=20, anchor="w")

label = ctk.CTkLabel(frame, text="Insert the URL:", font=font)
label.pack(side="left", padx=(0,10))





urlEntry = ctk.CTkEntry(frame, width=250, font=("Helvetica", 16),placeholder_text='https://youtube.com/...')
urlEntry.pack(side="left")

# Frame per pulsanti
button_frame = ctk.CTkFrame(root, fg_color="transparent")
button_frame.pack(pady=10)

selFolderB = ctk.CTkButton(button_frame, text="Select Folder", corner_radius=20, font=font,command=choose_folder)
selFolderB.pack(side="left", padx=10)

clearFolderB = ctk.CTkButton(button_frame, text="Clear folder selection ", corner_radius=20, font=font,command=clear_folder)
clearFolderB.pack(side="left", padx=10)

# Area di testo in sola lettura
folderPathArea = ctk.CTkTextbox(root, height=30, width=350)
folderPathArea.configure(state="disabled", font=font)
folderPathArea.pack(padx=20, pady=20)

# Label sopra radio buttons
radio_label = ctk.CTkLabel(root, text="File format:", font=("Helvetica", 16))
radio_label.place(relx=0.5, rely=0.60, anchor="center")

# Radio buttons frame
radio_frame = ctk.CTkFrame(root, fg_color="transparent")
radio_frame.place(relx=0.5, rely=0.70, anchor="center")

radio_var = tk.StringVar(value="Option2")

radio1 = ctk.CTkRadioButton(radio_frame, text="MP4 (video & audio)", variable=radio_var, value="Option1", font=("Helvetica", 16))
radio1.pack(side="left", padx=10)

radio2 = ctk.CTkRadioButton(radio_frame, text="MP3 (audio only)", variable=radio_var, value="Option2", font=("Helvetica", 16))
radio2.pack(side="left", padx=10)

# Pulsante di conferma
confirm_button = ctk.CTkButton(root, text="Download", corner_radius=20, font=("Helvetica", 16),command=checkAndDownload)
confirm_button.place(relx=0.5, rely=0.85, anchor="center")

windll.shcore.SetProcessDpiAwareness(1)
root.mainloop()