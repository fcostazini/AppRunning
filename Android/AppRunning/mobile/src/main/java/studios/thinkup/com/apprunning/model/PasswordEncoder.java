package studios.thinkup.com.apprunning.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Facundo on 17/08/2015.
 */
public class PasswordEncoder {

    public static  String encodePass(String pass) {

            String generatedPassword = null;
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-1");
                String salt = "H0L4Mund0";
                md.update(salt.getBytes());
                byte[] bytes = md.digest(pass.getBytes());
                StringBuilder sb = new StringBuilder();
                for(int i=0; i< bytes.length ;i++)
                {
                    sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
                }
                generatedPassword = sb.toString();
            }
            catch (NoSuchAlgorithmException e)
            {
                e.printStackTrace();
            }
            return generatedPassword;

    }
}
