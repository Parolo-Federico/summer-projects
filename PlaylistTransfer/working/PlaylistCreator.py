from googleapiclient.discovery import build
from google_auth_oauthlib.flow import InstalledAppFlow
import urllib.parse
import time

# --- CONFIGURATION ---
SCOPES = ["https://www.googleapis.com/auth/youtube.force-ssl"]
PLAYLIST_TITLE = "try 1"
PLAYLIST_DESCRIPTION = "cool description"

# --- AUTHENTICATION ---
flow = InstalledAppFlow.from_client_secrets_file("client_secret.json", SCOPES)
credentials = flow.run_local_server(port=8080)
youtube = build("youtube", "v3", credentials=credentials)

# --- CREATE PLAYLIST ---
playlist_request = youtube.playlists().insert(
    part="snippet,status",
    body={
        "snippet": {
            "title": PLAYLIST_TITLE,
            "description": PLAYLIST_DESCRIPTION
        },
        "status": {
            "privacyStatus": "private"
        }
    }
)
playlist_response = playlist_request.execute()
playlist_id = playlist_response["id"]

print(f"Playlist created: {PLAYLIST_TITLE}")

# --- READ SONGS FROM FILE ---
with open("playlist.txt", "r", encoding="utf-8") as f:
    songs = [line.strip() for line in f if line.strip()]

# --- SEARCH & ADD SONGS ---
for song in songs:
    query = urllib.parse.quote(song)
    search_response = youtube.search().list(
        part="snippet",
        q=song,
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
        print(f"Added: {song}")
        time.sleep(1)
    else:
        print(f"Not found: {song}")

print("Import complete!")
