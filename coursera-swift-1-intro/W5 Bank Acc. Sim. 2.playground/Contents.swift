class VirtualBank {
    enum AccountType {case credit, debit, notCreated}
    var accountType = AccountType.notCreated
    
    func bankStartup() {
        print("Welcome to your virtual bank system.")
    }
              
    func openAccount() {
      print("What kind of account would you like to open?")
      print("1. Debit account")
      print("2. Credit account")
    }
    
    func isAccountCreated() -> Bool {
        if accountType == AccountType.notCreated {
            return false
        }
        return true
    }
    
    func createAccount(numberPadKey: Int) {
        print("The selected option is: \(numberPadKey)")

        switch(numberPadKey) {
            case 1:
                accountType = AccountType.debit
                break
            case 2:
                accountType = AccountType.credit
                break
            default:
                print("Invalid input: \(numberPadKey)")
                return // avoids print msg below if not opened
        }
        print("You have opened a \(accountType) account.")

    }
}

let virtualBank = VirtualBank()
virtualBank.bankStartup()

repeat { // doesn't really make a diference here but it's good practice
    virtualBank.openAccount()
    var simulatedUserChoice = Int.random(in: 1...5)
    virtualBank.createAccount(numberPadKey: simulatedUserChoice)
} while !virtualBank.isAccountCreated()


struct BankAccount {
    var debitBalance = 0
    var creditBalance = 0
    let creditLimit = 100
    
    var debitBalanceInfo: String {
        "Debit balance: $\(debitBalance)"
    }
    var availableCredit: Int {
        creditLimit + creditBalance
    }
    var creditBalanceInfo: String {
        "Available credit: $\(availableCredit)"
    }
    
    mutating func debitDeposit(_ amount: Int) {
        debitBalance += amount
        print("Deposited $\(amount). \(debitBalanceInfo)")
    }
    
    mutating func creditDeposit(_ amount: Int) {
        creditBalance += amount
        print("Credit $\(amount). \(creditBalanceInfo)")
        if creditBalance == 0 {
            print("Paid off credit balance.")
        } else if creditBalance > 0 {
            print("Overpaid credit balance.")
        }
    }
    
    mutating func debitWithdraw(_ amount: Int) {
        if amount > debitBalance {
            print("Insufficient fund to withdraw $\(amount). \(debitBalanceInfo)")
        } else {
            debitBalance -= amount
            print("Debit withdraw: $\(amount). \(debitBalanceInfo)")
        }
    }
    
    mutating func creditWithdraw(_ amount: Int) {
        if amount > availableCredit {
            print("Insufficient credit to withdraw $\(amount). \(creditBalanceInfo)")
        } else {
            creditBalance -= amount
            print("Credit withdraw: $\(amount). \(creditBalanceInfo)")
        }
    }
}

var bankAccount = BankAccount()
print(bankAccount.debitBalanceInfo)
bankAccount.debitDeposit(100)
bankAccount.debitWithdraw(20)
bankAccount.debitWithdraw(81)
print(bankAccount.creditBalanceInfo)
bankAccount.creditWithdraw(101)
bankAccount.creditWithdraw(100)
bankAccount.creditDeposit(50)
bankAccount.creditDeposit(50)
bankAccount.creditDeposit(50)
