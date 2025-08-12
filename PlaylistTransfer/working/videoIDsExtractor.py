from googleapiclient.discovery import build
from google_auth_oauthlib.flow import InstalledAppFlow
import urllib.parse
import time

# ===== CONFIG =====
SCOPES = ["https://www.googleapis.com/auth/youtube.force-ssl"]
INPUT_FILE = "playlist.txt"   # Song Title - Artist
OUTPUT_FILE = "video_ids.txt"

# ===== AUTHENTICATION =====
flow = InstalledAppFlow.from_client_secrets_file("client_secret.json", SCOPES)
credentials = flow.run_local_server(port=8080)
youtube = build("youtube", "v3", credentials=credentials)

# ===== READ SONGS =====
with open(INPUT_FILE, "r", encoding="utf-8") as f:
    songs = [line.strip() for line in f if line.strip()]

video_ids = []

# ===== SEARCH EACH SONG ONCE =====
for song in songs:
    try:
        search_response = youtube.search().list(
            part="snippet",
            q=song,
            maxResults=1,
            type="video"
        ).execute()

        if search_response["items"]:
            vid = search_response["items"][0]["id"]["videoId"]
            video_ids.append(vid)
            print(f"Found: {song} → {vid}")
        else:
            print(f"❌ Not found: {song}")

        # Safety: avoid hitting per-second rate limits
        time.sleep(0.2)

    except Exception as e:
        print(f"Error searching for {song}: {e}")

# ===== SAVE VIDEO IDS =====
with open(OUTPUT_FILE, "w", encoding="utf-8") as f:
    for vid in video_ids:
        f.write(vid + "\n")

print(f"✅ Saved {len(video_ids)} video IDs to {OUTPUT_FILE}")
