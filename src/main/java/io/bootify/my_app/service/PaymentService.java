package io.bootify.my_app.service;

import io.bootify.my_app.domain.Contract;
import io.bootify.my_app.domain.Payment;
import io.bootify.my_app.model.PaymentDTO;
import io.bootify.my_app.repos.ContractRepository;
import io.bootify.my_app.repos.PaymentRepository;
import io.bootify.my_app.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ContractRepository contractRepository;

    public PaymentService(final PaymentRepository paymentRepository,
            final ContractRepository contractRepository) {
        this.paymentRepository = paymentRepository;
        this.contractRepository = contractRepository;
    }

    public List<PaymentDTO> findAll() {
        final List<Payment> payments = paymentRepository.findAll(Sort.by("id"));
        return payments.stream()
                .map(payment -> mapToDTO(payment, new PaymentDTO()))
                .toList();
    }

    public PaymentDTO get(final Long id) {
        return paymentRepository.findById(id)
                .map(payment -> mapToDTO(payment, new PaymentDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PaymentDTO paymentDTO) {
        final Payment payment = new Payment();
        mapToEntity(paymentDTO, payment);
        return paymentRepository.save(payment).getId();
    }

    public void update(final Long id, final PaymentDTO paymentDTO) {
        final Payment payment = paymentRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(paymentDTO, payment);
        paymentRepository.save(payment);
    }

    public void delete(final Long id) {
        paymentRepository.deleteById(id);
    }

    private PaymentDTO mapToDTO(final Payment payment, final PaymentDTO paymentDTO) {
        paymentDTO.setId(payment.getId());
        paymentDTO.setDate(payment.getDate());
        paymentDTO.setAmount(payment.getAmount());
        paymentDTO.setContract(payment.getContract() == null ? null : payment.getContract().getId());
        return paymentDTO;
    }

    private Payment mapToEntity(final PaymentDTO paymentDTO, final Payment payment) {
        payment.setDate(paymentDTO.getDate());
        payment.setAmount(paymentDTO.getAmount());
        final Contract contract = paymentDTO.getContract() == null ? null : contractRepository.findById(paymentDTO.getContract())
                .orElseThrow(() -> new NotFoundException("contract not found"));
        payment.setContract(contract);
        return payment;
    }

}
