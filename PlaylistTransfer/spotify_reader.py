import spotipy
from spotipy.oauth2 import SpotifyOAuth

# Configura i dati della tua app Spotify
CLIENT_ID = 'a749ced1ff8f402cbcb07634b7bf3ed2'
CLIENT_SECRET = '04808cdf60ba4591a4b51322dff05255'
REDIRECT_URI = 'http://192.168.1.18:8888/callback'

# Scopes per leggere playlist private
SCOPE = 'playlist-read-private'

def salva_playlist_in_file(username, playlist_id, filename):
    sp = spotipy.Spotify(auth_manager=SpotifyOAuth(
        client_id=CLIENT_ID,
        client_secret=CLIENT_SECRET,
        redirect_uri=REDIRECT_URI,
        scope=SCOPE,
        username=username
    ))

    results = sp.playlist_items(playlist_id)
    tracks = results['items']

    # Gestione paginazione se la playlist è lunga
    while results['next']:
        results = sp.next(results)
        tracks.extend(results['items'])

    with open(filename, 'w', encoding='utf-8') as f:
        for item in tracks:
            track = item['track']
            nome = track['name']
            artisti = ', '.join([art['name'] for art in track['artists']])
            f.write(f"{nome} - {artisti}\n")

    print(f"Playlist salvata in {filename}")

if __name__ == "__main__":
    username = input("Inserisci il tuo username Spotify: ")
    playlist_id = input("Inserisci l'ID o URL della playlist: ")
    filename = "playlist_canzoni.txt"
    salva_playlist_in_file(username, playlist_id, filename)
