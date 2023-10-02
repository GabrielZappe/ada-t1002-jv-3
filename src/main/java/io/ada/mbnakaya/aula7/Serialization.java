import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class Serialization {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Path path = Path.of("src/main/resources/aula7/payment.txt");

        // Objeto que será serializado e gravado em arquivo txt
        Payment payment = new Payment(1L, 0L, 10L, "BRL");

        // Serialização
        try (FileChannel channel = FileChannel.open(path, StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(payment);

            ByteBuffer buffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
            channel.write(buffer);
        } catch (NotSerializableException ex) {
            System.out.println("Objeto não serializável :(");
        }

        // Deserialização
        try (FileChannel channel = FileChannel.open(path, StandardOpenOption.READ)) {
            ByteBuffer buffer = ByteBuffer.allocate((int) channel.size());
            channel.read(buffer);
            buffer.flip();

            byte[] data = new byte[buffer.remaining()];
            buffer.get(data);

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
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
