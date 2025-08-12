from googleapiclient.discovery import build
from google_auth_oauthlib.flow import InstalledAppFlow
import time

# === CONFIG ===
SCOPES = ["https://www.googleapis.com/auth/youtube.force-ssl"]
PLAYLIST_ID = "YOUR_EXISTING_PLAYLIST_ID"  # Replace with your playlist ID
INPUT_FILE = "playlist.txt"                 # Lines like "Song Title - Artist"

# === AUTHENTICATION ===
flow = InstalledAppFlow.from_client_secrets_file("client_secret.json", SCOPES)
credentials = flow.run_local_server(port=8080)
youtube = build("youtube", "v3", credentials=credentials)

# === READ SONGS FROM FILE ===
with open(INPUT_FILE, "r", encoding="utf-8") as f:
    songs = [line.strip() for line in f if line.strip()]

# === PROCESS EACH SONG ===
for song in songs:
    try:
        # Search YouTube for the song
        search_response = youtube.search().list(
            part="snippet",
            q=song,
            maxResults=1,
            type="video"
        ).execute()

        if not search_response["items"]:
            print(f"❌ No video found for: {song}")
            continue

        video_id = search_response["items"][0]["id"]["videoId"]

        # Add video to the playlist
        youtube.playlistItems().insert(
            part="snippet",
            body={
                "snippet": {
                    "playlistId": PLAYLIST_ID,
                    "resourceId": {
                        "kind": "youtube#video",
                        "videoId": video_id
                    }
                }
            }
        ).execute()

        print(f"✅ Added '{song}' as video ID {video_id}")

        # To avoid quota spikes and rate limiting
        time.sleep(0.2)

    except Exception as e:
        print(f"Error processing '{song}': {e}")

print("All done!")
