package softuni.exam.instagraphlite.service;

public class ProjectConstants {

    private static final String INPUT_FILES_PATH = "src/main/resources/files/";

    private static final String INPUT_PICTURE_FILE_NAME = "pictures.json";
    private static final String INPUT_USERS_FILE_NAME = "users.json";
    private static final String INPUT_POSTS_FILE_NAME = "posts.xml";

    public static String getPictureInputFile() {
        return INPUT_FILES_PATH + INPUT_PICTURE_FILE_NAME;
    }

    public static String getUsersInputFile() {
        return INPUT_FILES_PATH + INPUT_USERS_FILE_NAME;
    }

    public static String getPostsInputFile() {
        return INPUT_FILES_PATH + INPUT_POSTS_FILE_NAME;
    }
}
