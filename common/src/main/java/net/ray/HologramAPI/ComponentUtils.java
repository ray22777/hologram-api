package net.ray.HologramAPI;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.*;

public class ComponentUtils {
    public static Component darkenComponent(Component component, float darkness) {
        MutableComponent darkened = MutableComponent.create(component.getContents());

        Style originalStyle = component.getStyle();
        Style darkenedStyle = originalStyle;

        if (originalStyle.getColor() != null) {
            int originalColor = originalStyle.getColor().getValue();
            int darkenedColor = darkenColor(originalColor, darkness);
            darkenedStyle = originalStyle.withColor(TextColor.fromRgb(darkenedColor));
        }

        darkened.setStyle(darkenedStyle);

        for (Component sibling : component.getSiblings()) {
            darkened.append(darkenComponent(sibling, darkness));
        }

        return darkened;
    }

    private static int darkenColor(int color, float darkness) {
        int rgb = color & 0xFFFFFF;
        int alpha = 0xFF000000;
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;
        red = (int)(red * (1.0f - darkness));
        green = (int)(green * (1.0f - darkness));
        blue = (int)(blue * (1.0f - darkness));
        return alpha | (red << 16) | (green << 8) | blue;
    }
    public static Component parseColorCodes(String text) {
        text = text.replace('&', '§');

        MutableComponent result = Component.literal("");
        Style currentStyle = Style.EMPTY;
        StringBuilder currentText = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            if (c == '§' && i + 1 < text.length()) {
                char code = text.charAt(++i);
                if (currentText.length() > 0) {
                    result.append(Component.literal(currentText.toString())
                            .withStyle(currentStyle));
                    currentText.setLength(0);
                }
                ChatFormatting formatting = ChatFormatting.getByCode(code);
                if (formatting != null) {
                    if (formatting == ChatFormatting.RESET) {
                        currentStyle = Style.EMPTY;
                    } else {
                        currentStyle = currentStyle.applyLegacyFormat(formatting);
                    }
                }
            } else {
                currentText.append(c);
            }
        }
        if (currentText.length() > 0) {
            result.append(Component.literal(currentText.toString())
                    .withStyle(currentStyle));
        }

        return result;
    }
}