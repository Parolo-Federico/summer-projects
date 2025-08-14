import requests
from bs4 import BeautifulSoup
import time

HEADERS = {
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64)"
}

def get_first_video_link(title, author):
    query = f"{title} {author}"
    url = "https://www.youtube.com/results"
    params = {'search_query': query}
    response = requests.get(url, params=params, headers=HEADERS)
    soup = BeautifulSoup(response.text, 'html.parser')

    for a in soup.find_all('a', href=True):
        href = a['href']
        if href.startswith("/watch?v="):
            return "https://www.youtube.com" + href
    return None

def main():
    input_file = 'playlist.txt'       # Your input file with "title - author" per line
    output_file = 'video_links.txt' # Output file to save results
    results = []

    with open(input_file, 'r', encoding='utf-8') as file:
        lines = file.readlines()

    for idx, line in enumerate(lines, start=1):
        line = line.strip()
        if not line:
            continue
        parts = line.split(' - ')
        if len(parts) < 2:
            print(f"[{idx}] Skipping invalid line: {line}")
            continue
        title, author = parts[0], parts[1]

        print(f"[{idx}] Searching for: {title} - {author}")
        link = get_first_video_link(title, author)

        if link:
            print(f"    Found: {link}")
            results.append((title, author, link))
        else:
            print(f"    No video found.")

        time.sleep(1)  # polite delay to avoid rate limiting

    # Save all results to output file
    with open(output_file, 'w', encoding='utf-8') as f_out:
        for title, author, link in results:
            f_out.write(f"{title} - {author} - {link}\n")

    print(f"\nDone! Found {len(results)} videos. Results saved to {output_file}")

    # Return Python list if you want to use it further
    return results

if __name__ == "__main__":
    video_list = main()
