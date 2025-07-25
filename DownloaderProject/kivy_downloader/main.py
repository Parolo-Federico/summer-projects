from kivy.app import App
from kivy.lang import Builder
from kivy.core.window import Window
from kivy.utils import platform
from kivy.uix.filechooser import FileChooserListView

if platform == "android":
    from android.permissions import request_permissions, Permission
    from android.storage import primary_external_storage_path

    def richiedi_permessi():
        request_permissions([
            Permission.READ_EXTERNAL_STORAGE,
            Permission.WRITE_EXTERNAL_STORAGE
        ])

# Imposta dimensioni e colore della finestra (scuro)
Window.size = (400, 400)
Window.clearcolor = (0.15, 0.15, 0.15, 1)  # Grigio scuro

class DownloaderApp(App):
    def build(self):
        return Builder.load_file("downloader.kv")

if __name__ == "__main__":
    DownloaderApp().run()
