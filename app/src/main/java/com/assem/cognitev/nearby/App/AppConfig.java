package com.assem.cognitev.nearby.App;

public class AppConfig {

    private static AppConfig appConfig;

    /**
     * The private constructor for the Customer List Singleton class
     */
    private AppConfig() {
    }

    public static AppConfig getInstance() {
        if (appConfig == null) {
            appConfig = new AppConfig();
        }
        return appConfig;
    }

    // Users
    public final String USERS = "users";
    public final String USERS_TOKEN = "token";
    public final String USERS_USERNAME = "username";
    public final String USERS_EMAIL = "email";
    public final String USERS_PASSWORD = "password";
    public final String USERS_IMG = "img";
    public final String DEFAULT = "default";
    public final String USERS_IS_PHG = "is_phg";
    public final String USERS_BIO = "bio";
    public final String USERS_LOCATION = "location";
    public final String USERS_PROFILE_IMAGES = "profile_images";
    public final String USER_FAV = "user_fav";
    public final String USER_FAV_ALBUM = "fav_albums";
    public final String USER_GENRES = "genres";
    public final String USER_AVAILABLE_START_DATE = "start_date";
    public final String USER_AVAILABLE_END_DATE = "end_date";
    public final String USER_SALARY = "salary";
    public final String USER_IS_AVAILABLE = "is_available";
    // Photographers
    public final String PHOTOGRAPHERS = "photographers";
    public final String PHOTOGRAPHERS_GENRES = "genres";
    // Albums
    public final String ALBUMS = "albums";
    public final String ALBUMS_USER_UID = "userUid";
    public final String ALBUM_IMG = "album_img";
    // Extras
    public final String ALBUM_ADAPTER_INTENT_EXTRAS_PHG_ID = "phg_id";
    public final String ALBUM_ADAPTER_INTENT_EXTRAS_ALBUM_OBJ = "album_obj";
    public final String CHAT_ADAPTER_INTENT_EXTRAS_USER_OBJ = "user_obj";
    // Chat
    public final String CHATS = "chats";
    public final String USER_CHAT_LIST = "user_chat_list";
    public final String CHATS_IS_SEEN = "seen";
    public final String CHATS_IS_ONLINE = "is_online";
    public final String CHATS_ONLINE = "online";
    public final String CHATS_OFFLINE = "online";
    public final String CHATS_ID = "id";
    public final String CHATS_KEY = "chat_key";
    public final String CHATS_USERS_TOKEN = "token";


}




  /*
    locked mode
    service cloud.firestore
    {
        match / databases / {database} / documents {
        match / {document = **}{
            allow read, write:if false;
        }
    }
    }
    */
    /*
    test mode

    service cloud.firestore {
  match /databases/{database}/documents {
    match /{document=**} {
      allow read, write;
    }
  }
}
     */