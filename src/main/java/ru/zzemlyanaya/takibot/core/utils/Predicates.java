package ru.zzemlyanaya.takibot.core.utils;

/* created by zzemlyanaya on 18/10/2022 */

import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.zzemlyanaya.takibot.domain.utils.Command;

import javax.validation.constraints.NotNull;
import java.util.function.Predicate;

public class Predicates {

    @NotNull
    public static Predicate<Update> hasCommand(Command command) {
        return upd -> upd.hasMessage() && upd.getMessage().getText().contains(command.getName());
    }

    @NotNull
    public static Predicate<Update> hasMessageWith(String text) {
        return upd -> upd.hasMessage() && upd.getMessage().getText().contains(text);
    }

    @NotNull
    public static Predicate<Update> hasNumericMessage() {
        return upd -> upd.hasMessage() && StringUtils.isNumeric(upd.getMessage().getText());
    }

    @NotNull
    public static Predicate<Update> isReplyToMessage(String message) {
        return upd -> {
            Message reply = upd.getMessage().getReplyToMessage();
            return reply != null && reply.hasText() && reply.getText().equalsIgnoreCase(message);
        };
    }

    @NotNull
    public static Predicate<Update> isReplyToMessageWith(String message) {
        return upd -> {
            Message reply = upd.getMessage().getReplyToMessage();
            return reply != null && reply.hasText() && reply.getText().contains(message);
        };
    }
}