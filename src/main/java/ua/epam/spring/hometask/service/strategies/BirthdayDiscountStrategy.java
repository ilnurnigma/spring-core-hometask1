package ua.epam.spring.hometask.service.strategies;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class BirthdayDiscountStrategy implements DiscountStrategy {
    @Override
    public byte getDiscount(@Nullable User user, @Nonnull Event event, @Nonnull LocalDateTime airDateTime, long numberOfTickets) {
        if (user == null) {
            return 0;
        }

        if (user.getDateOfBirth() == null) {
            return 0;
        }

        if (airDateTime.toLocalDate().plusDays(5).isAfter(user.getDateOfBirth())
                && airDateTime.toLocalDate().minusDays(5).isBefore(user.getDateOfBirth())) {
            return 5;
        }

        return 0;
    }
}
