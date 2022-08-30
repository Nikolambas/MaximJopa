package Clases;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class Bank {
    String name;
    String address;
    String INN;
    String OGRN;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bank bank = (Bank) o;
        return name.equals(bank.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public Bank(String name) {
        this.name = name;
    }
}
