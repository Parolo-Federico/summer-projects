import tkinter as tk
from tkinter import ttk
from ctypes import windll
import customtkinter as ctk
import yt_dlp

#tutorial: https://www.pythontutorial.net/tkinter/tkinter-ttk/
'''
#root e il nome standard ma si può scrivere qualsiasi cosa
#crea l'istanza della finestra
root = tk.Tk()

root.title("Video downloader") # imposta il titolo alla finestra
root.title() # senza argomenti restituisce quello corrente


geometry = root.geometry()
#print(geometry)
# format: widthxheight+-x+-y
# per ridimensionare la finestra
root.geometry('600x400+50+50')
# per centrarla nello schermo

##prende le dimensioni dello schermo in cui si lavora
screenWidth = root.winfo_screenwidth()
screenHeight = root.winfo_screenheight()


# per impostare la finestra al centro con dimensioni scelte

windowWidth = 600
windowHeight = 400

# punti centrali
centerX = int(screenWidth/2 - windowWidth/2)
centerY = int(screenHeight/2 - windowHeight/2)

# imposta la finestra al centro
root.geometry(f'{windowWidth}x{windowHeight}+{centerX}+{centerY}')

# di default a 'True' per far modificare dimensioni finestra
root.resizable(False,False)

# è possibile rendere opaca o trasparente una finestra
# 0=trasparente    1=opaco
# root.attributes('-alpha',0.5)

# per spostare avanti o indietro la finestra:
# root.attributes('-topmost',1) # sempre davanti
# lift() e lower() per spostare avanti o indietro

# Cambiare icona all finestra
root.iconbitmap()
# immagine in formato .ico con relativo path raggiungibile




#Widgets



#crea un label con parent la finestra creata e un testo
#sintassi generale:  widget = WidgetName(master,kw)
#kw = argomenti master= parent a cui assegno il widget
label = tk.Label(root, text="Video/Audio downloader")

#posiziona il widget nella finestra
label.pack()


# ttk per quelli nuovi più fighi

tk.Label(root, text='Classic Label').pack()
ttk.Label(root, text='Themed Label').pack()
tk.Button(root,text='Default button').pack()
ttk.Button(root,text='Button++').pack()

#modifica widget 3 modi:
label = ttk.Label(root,text='a') # nel costruttore
label.config(text='a') # metodo config
label['text'] = 'a' # dizionario incorporato

def button_clicked():
    print('cliccato')
def button_clicked(str):
    print(str)
# per quando un pulsante è cliccato
# ttk.Button(root,text='click',command=button_clicked).pack()
# se voglio passare argomenti però:
 ex:
ttk.Button(
    root,
    text='Button',
    command=lambda: callback(args)
)

b1 =ttk.Button(
    root,text='click2',
    command=lambda: button_clicked('click2'))
b1.pack()

#font
# font= ("nome",dimensione) è un parametro dei widget

#immagini
#photo = tk.PhotoImage(file='path')
# attributo image=photo
# in un label serve poi l' attributo compound
# compound='text' mostra sono il testo oppure compound='image' solo l'immagine
# altri nel tutorial



#per far apparire la finestra non sfocata
windll.shcore.SetProcessDpiAwareness(1)

#root.mainloop()
'''
root = ctk.CTk()
ctk.set_appearance_mode('dark')
ctk.set_default_color_theme('green')
root.title('prova')
root.geometry('600x400')




root.mainloop()

