package org.hni.payment.dao;

import java.util.List;

import org.hni.admin.service.dto.PaymentInstrumentDto;
import org.hni.common.dao.BaseDAO;
import org.hni.payment.om.PaymentInstrument;

public interface GiftCardDAO extends BaseDAO<PaymentInstrument> {
 List<PaymentInstrumentDto> getGitCardsFor(Long providerId);
 List<String> getSerialCardNumbers(Long providerId);
}
