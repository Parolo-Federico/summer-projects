from flask import Flask, request, redirect
import spotipy
from spotipy.oauth2 import SpotifyOAuth
from google_auth_oauthlib.flow import Flow
from googleapiclient.discovery import build
import google.auth.transport.requests

import os

# === CONFIG ===
SPOTIPY_CLIENT_ID = "a749ced1ff8f402cbcb07634b7bf3ed2"
SPOTIPY_CLIENT_SECRET = "04808cdf60ba4591a4b51322dff05255"
SPOTIPY_REDIRECT_URI = "https://45ca5e3501fe.ngrok-free.app/spotify_callback"

YOUTUBE_REDIRECT_URI = "https://45ca5e3501fe.ngrok-free.app/youtube_callback"
YOUTUBE_CLIENT_SECRETS_FILE = "client_secret.json"
YOUTUBE_SCOPES = ["https://www.googleapis.com/auth/youtube"]

# === FLASK ===
app = Flask(__name__)

# === SPOTIFY OAUTH ===
sp_oauth = SpotifyOAuth(
    client_id=SPOTIPY_CLIENT_ID,
    client_secret=SPOTIPY_CLIENT_SECRET,
    redirect_uri=SPOTIPY_REDIRECT_URI,
    scope="playlist-read-private"
)

sp = None
tracks = []

@app.route("/")
def index():
    return '<a href="/spotify_login">Autenticati con Spotify</a>'

@app.route("/spotify_login")
def spotify_login():
    auth_url = sp_oauth.get_authorize_url()
    return redirect(auth_url)

@app.route("/spotify_callback")
def spotify_callback():
    global sp, tracks
    code = request.args.get("code")
    token_info = sp_oauth.get_access_token(code)
    sp = spotipy.Spotify(auth=token_info['access_token'])
    
    # Esempio: prendi la prima playlist dell'utente
    playlists = sp.current_user_playlists(limit=1)
    playlist = playlists['items'][0]
    playlist_id = playlist['id']
    playlist_name = playlist['name']
    results = sp.playlist_tracks(playlist_id)

    tracks = []
    for item in results['items']:
        track = item['track']
        track_name = track['name']
        artist_name = track['artists'][0]['name']
        tracks.append(f"{track_name} {artist_name}")

    return f'''
        ✅ Trovati {len(tracks)} brani nella playlist: {playlist_name}<br>
        <a href="/youtube_login">Passa a YouTube</a>
    '''

# === YOUTUBE OAUTH ===
@app.route("/youtube_login")
def youtube_login():
    flow = Flow.from_client_secrets_file(
        YOUTUBE_CLIENT_SECRETS_FILE,
        scopes=YOUTUBE_SCOPES,
        redirect_uri=YOUTUBE_REDIRECT_URI
    )
    auth_url, state = flow.authorization_url(
        access_type="offline",
        include_granted_scopes="true"
    )
    # Salva lo state in sessione se vuoi essere rigoroso
    app.config['OAUTH_STATE'] = state
    return redirect(auth_url)

@app.route("/youtube_callback")
def youtube_callback():
    flow = Flow.from_client_secrets_file(
        YOUTUBE_CLIENT_SECRETS_FILE,
        scopes=YOUTUBE_SCOPES,
        redirect_uri=YOUTUBE_REDIRECT_URI,
        state=app.config['OAUTH_STATE']
    )
    flow.fetch_token(authorization_response=request.url)

    credentials = flow.credentials
    youtube = build('youtube', 'v3', credentials=credentials)

    # 1. Crea playlist
    new_playlist = youtube.playlists().insert(
        part="snippet,status",
        body={
            "snippet": {
                "title": "Playlist Importata da Spotify",
                "description": "Creata automaticamente!"
            },
            "status": {
                "privacyStatus": "private"
            }
        }
    ).execute()

    playlist_id = new_playlist["id"]

    # 2. Cerca e aggiungi i brani
    for query in tracks:
        search_response = youtube.search().list(
            q=query,
            part="id",
            maxResults=1,
            type="video"
        ).execute()

        if search_response["items"]:
            video_id = search_response["items"][0]["id"]["videoId"]
            youtube.playlistItems().insert(
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
            ).execute()

    return f"✅ Playlist creata su YouTube con {len(tracks)} brani!"

if __name__ == "__main__":
    app.run(port=8888)

