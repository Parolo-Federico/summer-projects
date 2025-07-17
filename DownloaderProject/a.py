import customtkinter as ctk
import threading
import yt_dlp
import queue
from tkinter import filedialog

ctk.set_appearance_mode("System")
ctk.set_default_color_theme("blue")


class App(ctk.CTk):
    def __init__(self):
        super().__init__()

        self.title("YouTube MP3 Downloader")
        self.geometry("600x300")

        self.q = queue.Queue()

        # URL Entry
        self.url_entry = ctk.CTkEntry(self, width=500, placeholder_text="Inserisci URL video")
        self.url_entry.pack(pady=10)

        # Seleziona Cartella
        self.folder_button = ctk.CTkButton(self, text="Seleziona Cartella", command=self.select_folder)
        self.folder_button.pack(pady=5)

        # Label per mostrare la cartella selezionata
        self.folder_label = ctk.CTkLabel(self, text="Nessuna cartella selezionata")
        self.folder_label.pack(pady=5)

        # Bottone Download
        self.download_button = ctk.CTkButton(self, text="Scarica", command=self.start_download)
        self.download_button.pack(pady=10)

        # ProgressBar
        self.progressbar = ctk.CTkProgressBar(self, width=500)
        self.progressbar.pack(pady=20)
        self.progressbar.set(0)

        # Label di stato
        self.status_label = ctk.CTkLabel(self, text="")
        self.status_label.pack(pady=5)

        self.download_folder = ""

        self.check_queue()

    def select_folder(self):
        folder_selected = filedialog.askdirectory()
        if folder_selected:
            self.download_folder = folder_selected
            self.folder_label.configure(text=f"Cartella: {folder_selected}")
        print('.'+folder_selected+'.')

    def progress_hook(self, d):
        if d['status'] == 'downloading':
            percent = d.get('_percent_str', '0%').strip().replace('%', '')
            try:
                percent_float = float(percent) / 100
                self.q.put(('progress', percent_float))
            except:
                pass
        elif d['status'] == 'finished':
            # Download finito, parte il post-processing
            self.q.put(('status', "Download completato. Conversione in corso..."))

    def postprocessor_hook(self, d):
        if d['status'] == 'finished':
            self.q.put(('progress', 1.0))
            self.q.put(('status', "Conversione completata!"))

    def start_download(self):
        url = self.url_entry.get()
        if not url:
            self.status_label.configure(text="Inserisci un URL valido.")
            return

        if not self.download_folder:
            self.status_label.configure(text="Seleziona una cartella di destinazione.")
            return

        self.status_label.configure(text="Download in corso...")

        ydl_opts = {
            'format': 'bestaudio/best',
            'outtmpl': self.download_folder + '/%(title)s.%(ext)s',
            'progress_hooks': [self.progress_hook],
            'postprocessor_hooks': [self.postprocessor_hook],
            'postprocessors': [{
                'key': 'FFmpegExtractAudio',
                'preferredcodec': 'mp3',
                'preferredquality': '192',
            }],
        }

        def download():
            with yt_dlp.YoutubeDL(ydl_opts) as ydl:
                ydl.download([url])

        threading.Thread(target=download, daemon=True).start()

    def check_queue(self):
        try:
            while True:
                item = self.q.get_nowait()
                if item[0] == 'progress':
                    self.progressbar.set(item[1])
                elif item[0] == 'status':
                    self.status_label.configure(text=item[1])
        except queue.Empty:
            pass
        self.after(100, self.check_queue)


if __name__ == "__main__":
    app = App()
    app.mainloop()
