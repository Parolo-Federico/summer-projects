import tkinter as tk
from tkinter import ttk
from tkinter.messagebox import showinfo
from tkinter import filedialog
from ctypes import windll
import yt_dlp
import os
global folder
def download(url,directory):
    ydl_opts = {
    'format': 'bestaudio/best',          # solo audio, qualità migliore
    ### TODO sistema folder
    'outtmpl': f'{directory}/%(title)s.%(ext)s',      # nome file = titolo video + salva in cartella Musica
    'postprocessors': [{
        'key': 'FFmpegExtractAudio',     # usa ffmpeg per estrarre audio
        'preferredcodec': 'mp3',         # codec di output
        'preferredquality': '192',       # qualità bitrate kbps
    }],
    }
    with yt_dlp.YoutubeDL(ydl_opts) as ydl:
        ydl.download([url])
        urlEntry.delete('1.0',tk.END)
def choose_folder():
    global folder
    folder = filedialog.askdirectory()
def clear_folder():
    global folder
    folder = ''



root =tk.Tk()
root.title('Video/Audio Downloader')
root.iconbitmap("./assets/icona.ico")
root.geometry('600x400+100+100')
root.resizable(False,False)
root.rowconfigure(0,weight=1)
root.rowconfigure(1,weight=1)
root.rowconfigure(2,weight=1)
root.columnconfigure(0,weight=1)
root.columnconfigure(1,weight=3)
root.columnconfigure(2,weight=1)
root.configure(bg='grey')


labelURL = ttk.Label(root,text='Insert the URL: ')

urlEntry = tk.Entry(root,font=('Helvetica',20))

downloadButton = ttk.Button(root,text='Download',command= lambda: download(urlEntry.get(),folder))


folderButton = ttk.Button(root,text='Choose folder',command=choose_folder)

clearButton = ttk.Button(root,text='Clear folder',command=clear_folder)

labelURL.grid(column=0,row=0,pady=10)
urlEntry.grid(column=1,row=0,pady=10,columnspan=2,ipadx=10)
folderButton.grid(column=0,row=1,pady=10,padx=20)
clearButton.grid(column=2,row=1,padx=20)
downloadButton.grid(column=1,row=2)

style = ttk.Style(root)
style.configure('TLabel',font=('Helvetica',20))
style.configure('TButton',font=('Helvatica',20))

windll.shcore.SetProcessDpiAwareness(1)
root.mainloop()
