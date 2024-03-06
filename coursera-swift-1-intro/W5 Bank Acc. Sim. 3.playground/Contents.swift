class VirtualBankSystem {
    enum AccountType {case credit, debit, notCreated}
    var accountType = AccountType.notCreated
    var isOpened = true
    
    func bankStartup() {
        print("Welcome to your virtual bank system.")
    }
              
    func onboardCustomerAccountOpening() {
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

        switch numberPadKey {
            case 1:
                accountType = AccountType.debit
                break
            case 2:
                accountType = AccountType.credit
                break
            default:
                print("Invalid input: \(numberPadKey)")
                return
        }
        print("You have opened a \(accountType) account.")

    }
    
    func moneyTransfer(transferType: String, transferAmount: Int, _ bankAccount: inout BankAccount) {
        switch transferType {
            case "withdraw":
                if accountType == AccountType.credit {
                    bankAccount.creditWithdraw(transferAmount)
                } else if accountType == AccountType.debit {
                    bankAccount.debitWithdraw(transferAmount)
                }
                break
            case "deposit":
                if accountType == AccountType.credit {
                    bankAccount.creditDeposit(transferAmount)
                } else if accountType == AccountType.debit {
                    bankAccount.debitDeposit(transferAmount)
                }
                break
            default:
                break
        }
    }
    
    func checkBalance(_ bankAccount: BankAccount) {
        switch accountType {
            case AccountType.credit:
                bankAccount.creditBalanceInfo
                break
            case AccountType.debit:
                bankAccount.debitBalanceInfo
                break
            default:
                break
        }
    }
}

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

let virtualBank = VirtualBankSystem()
virtualBank.bankStartup()

repeat { // doesn't really make a diference here but it's good practice
    virtualBank.onboardCustomerAccountOpening()
    var simulatedUserChoice = Int.random(in: 1...5)
    virtualBank.createAccount(numberPadKey: simulatedUserChoice)
} while !virtualBank.isAccountCreated()


let transferAmount = 50
print("Transfer amount: $\(transferAmount)")
var bankAccount = BankAccount()

repeat {
    print("""
    What would you like to do?
    1. Check bank account
    2. Withdraw money
    3. Deposit money
    4. Close the system
    """)
    var simulatedUserChoice = Int.random(in: 1...5)
    print("The selected option is: \(simulatedUserChoice)")
    switch simulatedUserChoice {
        case 1:
            virtualBank.checkBalance(bankAccount)
            break
        case 2:
            virtualBank.moneyTransfer(transferType: "withdraw", transferAmount: transferAmount, &bankAccount)
            break
        case 3:
            virtualBank.moneyTransfer(transferType: "deposit", transferAmount: transferAmount, &bankAccount)
            break
        case 4:
            virtualBank.isOpened = false
            print("The system is closed.")
            break
        default:
            print("Invalid option.")
            break
    }
    
} while virtualBank.isOpened
