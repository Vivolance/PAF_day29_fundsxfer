package IBF.serverstub.models;

public class Account {

	private String account;
	private float balance;

	public void setName(String account) { this.account = account; }
	public String getName() { return this.account; }

	public void setBalance(float balance) { this.balance = balance; }
	public float getBalance() { return this.balance; }
}