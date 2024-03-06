var password = "1234"
let passcode = Int(password)

print(passcode!)

password = "hello world"
let passcode2 = Int(password)

if let code = passcode2 {
    print(code)
} else {
    print("Invalid passcode!")
}
    
let accessCode: Int
if let code = passcode2 {
    accessCode = code
    print(code)
} else {
    accessCode = 1111
    print(accessCode)
}

let firstPassword = "hello"
let secondPassword = "world"

if let firstPasscode = Int(firstPassword), let secondPasscode = Int(secondPassword) {
    print("first passcode \(firstPasscode) / second passcode \(secondPasscode)")
} else {
    print("Invalid passcodes!")
}

let firstAccessCode, secondAccessCode: Int
if let firstPasscode = Int(firstPassword), let secondPasscode = Int(secondPassword) {
    firstAccessCode = firstPasscode
    secondAccessCode = secondPasscode
} else {
    firstAccessCode = 1111
    secondAccessCode = 2222
    print("first passcode \(firstAccessCode) / second passcode \(secondAccessCode)")
}




    
