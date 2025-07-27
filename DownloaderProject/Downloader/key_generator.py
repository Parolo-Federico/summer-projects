import base64
SECRET_KEY = "q3X2mB#eRt@P9u"
def encrypt_data(data: str) -> str:
    xor_result = ''.join(chr(ord(c) ^ ord(SECRET_KEY[i % len(SECRET_KEY)])) for i, c in enumerate(data))
    encoded = base64.b64encode(xor_result.encode("utf-8")).decode("utf-8")
    return encoded

def decrypt_data(encoded: str) -> str:
    try:
        decoded = base64.b64decode(encoded.encode("utf-8")).decode("utf-8")
        original = ''.join(chr(ord(c) ^ ord(SECRET_KEY[i % len(SECRET_KEY)])) for i, c in enumerate(decoded))
        return original
    except Exception as e:
        print("Errore nella decriptazione:", e)
        return ""
    
if __name__ == "__main__":
    key = input("write key to crypt:>")
    encrypted_key = encrypt_data(key)
    print("encrypted key: (start)" + encrypted_key + "(end)")
    print("decrypted key: " + decrypt_data(encrypted_key))