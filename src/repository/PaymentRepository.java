package repository;

import mapper.PaymentDataMapper;
import model.Payment;
import storage.PaymentData;
import storage.PaymentJsonStorage;

import java.util.ArrayList;

public class PaymentRepository {

    private ArrayList<Payment> payments = new ArrayList<>();

    private PaymentJsonStorage storage;
    private PaymentDataMapper mapper;

    public PaymentRepository(PaymentJsonStorage storage,
                             PaymentDataMapper mapper) {

        if (storage == null) {
            throw new IllegalArgumentException(
                    "PaymentJsonStorage không hợp lệ"
            );
        }

        if (mapper == null) {
            throw new IllegalArgumentException(
                    "PaymentDataMapper không hợp lệ"
            );
        }

        this.storage = storage;
        this.mapper = mapper;

        loadFromStorage();
    }

    // Đọc Payment từ file JSON khi chương trình khởi động
    private void loadFromStorage() {

        ArrayList<PaymentData> dataList =
                storage.load();

        for (PaymentData data : dataList) {

            try {

                Payment payment =
                        mapper.toPayment(data);

                payments.add(payment);

            } catch (IllegalArgumentException e) {

                System.out.println(
                        "Không thể tải thanh toán "
                                + data.getPaymentId()
                                + ": "
                                + e.getMessage()
                );
            }
        }
    }

    // Thêm Payment
    public void addPayment(Payment payment) {

        if (payment == null) {
            throw new IllegalArgumentException(
                    "Thanh toán không hợp lệ"
            );
        }

        if (payment.getPaymentId() == null ||
                payment.getPaymentId().isEmpty()) {

            throw new IllegalArgumentException(
                    "Mã thanh toán không hợp lệ"
            );
        }

        if (findById(payment.getPaymentId()) != null) {

            throw new IllegalArgumentException(
                    "Mã thanh toán đã tồn tại"
            );
        }

        payments.add(payment);

        // Lưu xuống JSON
        saveToStorage();
    }

    // Lấy tất cả Payment
    public ArrayList<Payment> getAllPayments() {
        return new ArrayList<>(payments);
    }

    // Tìm Payment theo ID
    public Payment findById(String paymentId) {

        if (paymentId == null ||
                paymentId.isEmpty()) {

            return null;
        }

        for (Payment payment : payments) {

            if (payment.getPaymentId()
                    .equalsIgnoreCase(paymentId)) {

                return payment;
            }
        }

        return null;
    }

    // Xóa Payment
    public boolean removePayment(String paymentId) {

        Payment payment = findById(paymentId);

        if (payment != null) {

            payments.remove(payment);

            // Cập nhật lại JSON
            saveToStorage();

            return true;
        }

        return false;
    }

    private void saveToStorage() {
        ArrayList<PaymentData> dataList =
                new ArrayList<>();

        for (Payment payment : payments) {

            PaymentData data = new PaymentData(
                    payment.getPaymentId(),
                    payment.getTicket().getTicketId(),
                    payment.getAmount(),
                    payment.getPaymentMethod(),
                    payment.getTransactionCode(),
                    payment.getStatus()
            );

            dataList.add(data);
        }

        storage.save(dataList);
    }

    public int getPaymentCount() {
        return payments.size();
    }

    public boolean isEmpty() {
        return payments.isEmpty();
    }

    public PaymentJsonStorage getStorage() {
        return storage;
    }

    public PaymentDataMapper getMapper() {
        return mapper;
    }
}