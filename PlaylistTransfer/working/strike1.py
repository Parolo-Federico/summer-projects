import yt_dlp
import time

def download_audio(title, author):
    query = f"{title} {author}"
    ydl_opts = {
        'format': 'bestaudio/best',
        'quiet': True,
        'outtmpl': './downloadBah/%(title)s.%(ext)s',  # save with video title as filename
        'postprocessors': [{  # extract audio using ffmpeg
            'key': 'FFmpegExtractAudio',
            'preferredcodec': 'mp3',  # you can change to 'm4a' or 'wav' if you want
            'preferredquality': '192',
        }],
        'default_search': 'ytsearch1',  # search and download top 1 result
        'noplaylist': True,
    }
    try:
        with yt_dlp.YoutubeDL(ydl_opts) as ydl:
            print(f"Downloading audio for: {query}")
            ydl.download([query])
            print("Download completed.\n")
    except Exception as e:
        print(f"Failed to download {query}: {e}")

def main():
    input_file = 'playlist.txt'  # your input file with "title - author" per line
    with open(input_file, 'r', encoding='utf-8') as f:
        lines = f.readlines()

    for idx, line in enumerate(lines, start=1):
        line = line.strip()
        if not line:
            continue
        parts = line.split(' - ')
        if len(parts) < 2:
            print(f"[{idx}] Skipping invalid line: {line}")
            continue
        title, author = parts[0], parts[1]
        download_audio(title, author)
        time.sleep(1)  # be polite, avoid hammering YouTube

if __name__ == '__main__':
    main()
