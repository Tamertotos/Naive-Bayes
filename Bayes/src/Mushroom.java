import java.util.Arrays;

public record Mushroom(String label, String[] attributes) {

    @Override
    public String toString() {
        return "Mushroom{" +
                "label=" + label +
                ", attributes=" + Arrays.toString(attributes) +
                '}';
    }
}
