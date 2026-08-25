/*
 * SPDX-License-Identifier: MIT
 */

package io.github.janguenter.bluemap.arseng.integration.ae2;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/** Keeps the optional AE2 API outside classes that must load without that add-on. */
public final class Ae2BridgeLifecycle {

    private static final String BRIDGE_CLASS =
            "io.github.janguenter.bluemap.arseng.integration.ae2.ArsEngAe2Bridge";

    private Ae2BridgeLifecycle() {
    }

    public static boolean register() {
        return invoke("register");
    }

    public static boolean activate() {
        return invoke("activate");
    }

    public static boolean deactivate() {
        return invoke("deactivate");
    }

    private static boolean invoke(String methodName) {
        try {
            Class<?> bridge = Class.forName(
                    BRIDGE_CLASS,
                    true,
                    Ae2BridgeLifecycle.class.getClassLoader()
            );
            Method method = bridge.getMethod(methodName);
            return Boolean.TRUE.equals(method.invoke(null));
        } catch (InvocationTargetException exception) {
            return false;
        } catch (ReflectiveOperationException | LinkageError | RuntimeException exception) {
            return false;
        }
    }
}
