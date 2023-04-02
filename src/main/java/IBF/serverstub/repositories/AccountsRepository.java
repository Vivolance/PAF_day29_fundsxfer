package IBF.serverstub.repositories;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import IBF.serverstub.Utils;
import IBF.serverstub.models.Account;
import IBF.serverstub.services.FundsTransferException;

@Repository
public class AccountsRepository {

	public static final String SQL_UPDATE_ACCOUNT = """
		update accounts set balance = balance + ? where account = ?
		""";

	public static final String SQL_FIND_USER_BY_NAME = """
		select * from accounts where account = ?
		""";

	@Autowired
	private JdbcTemplate template;

	public Optional<Account> findAccountByName(String account) {
		SqlRowSet rs = template.queryForRowSet(SQL_FIND_USER_BY_NAME, account);
		if (!rs.next())
			return Optional.empty();
		return Optional.of(Utils.toAccount(rs));
	}

	public void deposit(String account, float amount) throws FundsTransferException{
		updateBalance(account, amount);
	}
	public void withdraw(String account, float amount) throws FundsTransferException{
		updateBalance(account, -amount);
	}
	public void updateBalance(String account, float amount) throws FundsTransferException{
		if (template.update(SQL_UPDATE_ACCOUNT, amount, account) <= 0)
			throw new FundsTransferException("cannot update %s".formatted(account));
	}
}