package com.pdrw.pdrw.subscription.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class SubscriptionDataForNotifyingDto {

    @NotEmpty(message = "Необходимо выбрать хотя бы один тип пользователя")
    private List<UserType> userTypes;

    @NotNull(message = "Необходимо выбрать срок подписки")
    private SubscriptionPeriod subscriptionPeriod;

    @NotNull(message = "Необходимо выбрать способ связи")
    private ContactMethod contactMethod;

    @NotEmpty(message = "Контактная информация обязательна")
    private String contactInfo;

    @NotNull(message = "Необходимо выбрать способ оплаты")
    private PaymentMethod paymentMethod;

    public enum UserType {
        PRIVATE("частное лицо"),
        MANUFACTURER("производитель мебели"),
        ENTREPRENEUR("индивидуальный предприниматель"),
        DISTRIBUTOR("дистрибьютор");

        private final String displayName;

        UserType(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }


    public enum SubscriptionPeriod {
        MONTH("месяц"),
        QUARTER("три месяца"),
        HALF_YEAR("полгода"),
        YEAR("год");

        private final String displayName;

        SubscriptionPeriod(String displayName) {
            this.displayName = displayName;
        }

    }


    public enum ContactMethod {
        TELEGRAM("telegram"),
        VIBER("viber"),
        EMAIL("email"),
        PHONE("phone");

        private final String displayName;

        ContactMethod(String displayName) {
            this.displayName = displayName;
        }

    }


    public enum PaymentMethod {
        CARD("картой онлайн"),
        CRYPTO("криптой"),
        CONTRACT("оплата по договору");

        private final String displayName;

        PaymentMethod(String displayName) {
            this.displayName = displayName;
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("╔═══════════════════════════════\n")
                .append("║ Запрос на подписку\n")
                .append("╠═══════════════════════════════\n")
                .append("║ Клиент: ");
        String userTypesStr = userTypes.stream()
                .map(UserType::getDisplayName)
                .collect(Collectors.joining(", "));
        builder.append(userTypesStr);

        builder.append("\n║ Длительность подписки: ")
                .append(subscriptionPeriod.displayName)
                .append("\n║ Способ связи: ")
                .append(contactMethod.displayName)
                .append("\n║ Контакт: ")
                .append(contactInfo)
                .append("\n║ Оплата: ")
                .append(paymentMethod.displayName)
                .append("\n╚═══════════════════════════════");

        return builder.toString();
    }
}
