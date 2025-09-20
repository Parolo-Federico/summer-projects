import spotipy
from spotipy.oauth2 import SpotifyClientCredentials

# --- CONFIGURATION ---
CLIENT_ID = "6e0c3a2f2bd64f2aabb0ab25a08810cf"
CLIENT_SECRET = "805d1875a0de4e79ac6032901a7879bc"
PLAYLIST_ID = "5e6pNrsrTlHpLGZPNYM4lG"  # e.g. 37i9dQZF1DXcBWIGoYBM5M
# https://open.spotify.com/playlist/1a1lTXHaFa6Qsh2INfpCWq?si=405e912cdb634004
# https://open.spotify.com/playlist/1cXjwobmSdiIuVvUnctPgV?si=d0c8d52f2a814b5c
#https://open.spotify.com/playlist/5e6pNrsrTlHpLGZPNYM4lG?si=27971f3ac4634a18
#https://open.spotify.com/playlist/6DwGhemGHLacZs1ruU2A53?si=996dc079d6764046

# --- AUTHENTICATION (no user login needed) ---
auth_manager = SpotifyClientCredentials(client_id=CLIENT_ID, client_secret=CLIENT_SECRET)
sp = spotipy.Spotify(auth_manager=auth_manager)

# --- FETCH PLAYLIST TRACKS ---
results = sp.playlist_tracks(PLAYLIST_ID)
songs = []

while results:
    for item in results['items']:
        track = item['track']
        if track is None:
            continue
        title = track['name']
        artist = track['artists'][0]['name']
        songs.append(f"{title} - {artist}")
    if results['next']:
        results = sp.next(results)
    else:
        break

# --- SAVE TO FILE ---
with open("playlist.txt", "w", encoding="utf-8") as f:
    f.write("\n".join(songs))

print("Playlist exported to playlist.txt")
