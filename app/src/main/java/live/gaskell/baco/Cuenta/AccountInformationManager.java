package live.gaskell.baco.Cuenta;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class AccountInformationManager extends Credenciales {


    public AccountInformationManager() {

    }

    public static String getAccountData(String key, Context activity) {
        return activity.getSharedPreferences(ACCOUNT_PREFERENCES, MODE_PRIVATE).getString(key, null);
    }

    public static void setAccountData(String key, String value, Context activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(ACCOUNT_PREFERENCES, MODE_PRIVATE);
        sharedPreferences.edit().putString(key, value).apply();
    }

    public static void clearDataUser(Context activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(ACCOUNT_PREFERENCES, MODE_PRIVATE);
        sharedPreferences.edit().clear();
    }
}
