import os
import google_auth_oauthlib.flow
import googleapiclient.discovery
import googleapiclient.errors

# Scopes richiesti: serve per creare playlist e aggiungere video
scopes = ["https://www.googleapis.com/auth/youtube.force-ssl"]

def authenticate_youtube():
    """
    Autenticazione OAuth2: devi avere un file client_secrets.json
    generato dalla Google Cloud Console.
    """
    flow = google_auth_oauthlib.flow.InstalledAppFlow.from_client_secrets_file(
        "client_secrets.json", scopes)
    try:
        credentials = flow.run_local_server(port=0)
    except Exception:
    # Fallback per ambienti senza browser
        credentials = flow.run_installed()
    youtube = googleapiclient.discovery.build("youtube", "v3", credentials=credentials)
    return youtube

def create_playlist(youtube, title, description=""):
    """
    Crea una nuova playlist privata.
    """
    request = youtube.playlists().insert(
        part="snippet,status",
        body={
            "snippet": {
                "title": title,
                "description": description
            },
            "status": {
                "privacyStatus": "private"
            }
        }
    )
    response = request.execute()
    print(f"Playlist creata: {response['id']}")
    return response['id']

def search_song(youtube, query):
    """
    Cerca una canzone su YouTube e restituisce il videoId del primo risultato.
    """
    request = youtube.search().list(
        part="snippet",
        q=query,
        maxResults=1,
        type="video"
    )
    response = request.execute()
    items = response.get('items', [])
    if not items:
        print(f"Nessun risultato per: {query}")
        return None
    return items[0]['id']['videoId']

def add_video_to_playlist(youtube, playlist_id, video_id):
    """
    Aggiunge un video alla playlist.
    """
    request = youtube.playlistItems().insert(
        part="snippet",
        body={
            "snippet": {
                "playlistId": playlist_id,
                "resourceId": {
                    "kind": "youtube#video",
                    "videoId": video_id
                }
            }
        }
    )
    request.execute()
    print(f"Aggiunto video: https://www.youtube.com/watch?v={video_id}")

def main():
    # Autenticazione
    youtube = authenticate_youtube()

    # Inserisci qui il titolo della playlist
    playlist_title = "Playlist 1"
    playlist_description = "Creata automaticamente con uno script Python"
    playlist_id = create_playlist(youtube, playlist_title, playlist_description)

    # Leggi il file di canzoni
    if not os.path.exists("canzoni.txt"):
        print("Il file canzoni.txt non esiste. Creane uno con una canzone per riga.")
        return

    with open("canzoni.txt", "r") as f:
        songs = [line.strip() for line in f.readlines() if line.strip()]

    # Cerca e aggiungi ogni canzone
    for song in songs:
        video_id = search_song(youtube, song)
        if video_id:
            add_video_to_playlist(youtube, playlist_id, video_id)
        else:
            print(f"Non trovata: {song}")

    print(f"Tutte le canzoni aggiunte! Playlist ID: {playlist_id}")

if __name__ == "__main__":
    main()
