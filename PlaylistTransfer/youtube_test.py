import spotipy
from spotipy.oauth2 import SpotifyOAuth

CLIENT_ID = 'a749ced1ff8f402cbcb07634b7bf3ed2'
CLIENT_SECRET = '04808cdf60ba4591a4b51322dff05255'
REDIRECT_URI = 'http://192.168.1.18:8888/callback'  # deve essere registrato, ma non useremo il server

SCOPE = 'playlist-read-private'

def main():
    sp_oauth = SpotifyOAuth(client_id=CLIENT_ID,
                            client_secret=CLIENT_SECRET,
                            redirect_uri=REDIRECT_URI,
                            scope=SCOPE,
                            show_dialog=True)

    auth_url = sp_oauth.get_authorize_url()
    print("Apri questa URL nel browser, fai login e autorizza l'app:")
    print(auth_url)
    print("\nDopo il login, verrai reindirizzato a un URL che contiene un parametro 'code'")
    code = input("Incolla qui il valore del parametro 'code' dall'URL: ").strip()

    token_info = sp_oauth.get_access_token(code)
    access_token = token_info['access_token']

    sp = spotipy.Spotify(auth=access_token)

    # Ora puoi usare sp normalmente
    user = sp.current_user()
    print(f"Autenticato come: {user['display_name']}")

if __name__ == "__main__":
    main()
