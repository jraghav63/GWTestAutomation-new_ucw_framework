package ucw.utils;

import ucw.enums.OSArch;

import java.io.IOException;

import static ucw.enums.OSArch.*;

public class OSUtils {
    public static OSArch getCurrentOS() {
        String currentOS = System.getProperty("os.name").toLowerCase();
        if (currentOS.contains("win")) {
            return WIN;
        } else if (currentOS.contains("lin")) {
            return LINUX;
        } else if (currentOS.contains("mac")) {
            return MAC;
        } else {
            throw new IllegalStateException("Undefined OS architecture type");
        }
    }

    public static void killChromeDriverProcesses() throws IOException {
        OSArch arch = getCurrentOS();
        switch (arch) {
            case LINUX -> {
                String[] cmd = new String[]{"/bin/sh", "./scripts/killchrome.sh"};
                Runtime.getRuntime().exec(cmd);
            }
            case WIN -> {
                Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
            }
        }
    }

}
