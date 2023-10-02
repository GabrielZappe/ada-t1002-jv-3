import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class Serialization {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Path path = Path.of("src/main/resources/aula7/payment.txt");
        if (!path.toFile().exists()) Files.createFile(path);

        // Objeto que será serializado e gravado em arquivo txt
        Payment payment = new Payment(1L, 0L, 10L, "BRL");

        // Serialização
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("src/main/resources/aula7/payment.txt"))) {
            objectOutputStream.writeObject(payment);
        } catch (NotSerializableException ex) {
            System.out.println("Objeto não serializável :(");
        }

        // Deserialização
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("src/main/resources/aula7/payment.txt"))) {
            var object = objectInputStream.readObject();
            if (object instanceof Payment) {
                Payment deserializedPayment = (Payment) object;
                System.out.println(deserializedPayment);
            }
        } catch (NotSerializableException ex) {
            System.out.println("Objeto não serializável :(");
        }
    }

    static class Payment implements Serializable {
        private static final long serialVersionUID = 1L;
        private Long id;
        private Long discount;
        private Long total;
        private String currency;

        public Payment(Long id, Long discount, Long total, String currency) {
            this.id = id;
            this.discount = discount;
            this.total = total;
            this.currency = currency;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getDiscount() {
            return discount;
        }

        public void setDiscount(Long discount) {
            this.discount = discount;
        }

        public Long getTotal() {
            return total;
        }

        public void setTotal(Long total) {
            this.total = total;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        @Override
        public String toString() {
            return "Payment{" +
                    "id=" + id +
                    ", discount=" + discount +
                    ", total=" + total +
                    ", currency='" + currency + '\'' +
                    '}';
        }
    }
}
