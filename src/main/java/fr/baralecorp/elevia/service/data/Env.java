package fr.baralecorp.elevia.service.data;

public enum Env {
    DEV("dev"), STAGING("staging"), PREPROD("preprod"), PROD("prod");

    private final String str_name;

    private Env(String str_name) {
        this.str_name = str_name;
    }

    public String getName() {
        return str_name;
    }

    /**
     * Return the Env enum from its name
     *
     * @param name
     * @return
     */
    public static Env getEnv(String name) {
        for (Env env : Env.values()) {
            if (env.getName().equals(name)) {
                return env;
            }
        }
        return null;
    }

}
