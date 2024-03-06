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


