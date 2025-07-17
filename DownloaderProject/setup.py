import customtkinter as ctk
class ToplevelWindow(ctk.CTkToplevel):
    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)
        self.geometry("400x300")

        self.label = ctk.CTkLabel(self, text="Downloading...")
        self.label.pack(padx=20, pady=20)
        self.progressBar = ctk.CTkProgressBar(self,mode='indeterminate',fg_color='blue',corner_radius=20,bg_color='transparent')


class App(ctk.CTk):
    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)
        self.geometry("500x400")

        self.button_1 = ctk.CTkButton(self, text="open toplevel", command=self.open_toplevel)
        self.button_1.pack(side="top", padx=20, pady=20)

        self.toplevel_window = None

    def open_toplevel(self):
        if self.toplevel_window is None or not self.toplevel_window.winfo_exists():
            self.toplevel_window = ToplevelWindow(self)  # create window if its None or destroyed
            self.toplevel_window.attributes('-topmost',1)
            self.toplevel_window.focus()
        else:
            self.toplevel_window.focus()  # if window exists focus it


app = App()
app.mainloop()