package ru.pnz.floridov.RestDemo.service;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pnz.floridov.RestDemo.exception.debetAccountException.DebetAccountNotFoundException;
import ru.pnz.floridov.RestDemo.model.Client;
import ru.pnz.floridov.RestDemo.model.DebetAccount;
import ru.pnz.floridov.RestDemo.repository.DebetAccountRepository;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DebetAccountService {

    DebetAccountRepository debetAccountRepository;

    @Autowired
    public DebetAccountService(DebetAccountRepository debetAccountRepository) {
        this.debetAccountRepository = debetAccountRepository;
    }


    public List<DebetAccount> findAll() {
        return debetAccountRepository.findAll();
    }


    public List<DebetAccount> findWithPagination(Integer page, Integer debetAccountPerPage) {
        return debetAccountRepository.findAll(PageRequest.of(page, debetAccountPerPage)).getContent();
    }


    public DebetAccount findOne(Long id) {
        Optional<DebetAccount> foundDebetAccount = debetAccountRepository.findById(id);
        return foundDebetAccount.orElseThrow(DebetAccountNotFoundException::new);
    }

    @Transactional
    public void save(DebetAccount debetAccount) {
        debetAccountRepository.save(debetAccount);
    }

    @Transactional
    public void update(Long id, DebetAccount updatedDebetAccount) {
        updatedDebetAccount.setId(id);
        debetAccountRepository.save(updatedDebetAccount);
    }

    @Transactional
    public void delete(Long id) {
        debetAccountRepository.deleteById(id);
    }


    public Client getClient(Long id) {
        // Здесь Hibernate.initialize() не нужен, так как владелец (сторона One) загружается нелениво
        return debetAccountRepository.findById(id).map(DebetAccount::getClient).orElse(null);
    }

}
