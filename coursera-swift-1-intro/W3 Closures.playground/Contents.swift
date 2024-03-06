var goldBars = 0

// let unlockTreasureChest = {(inventory: Int) -> Int in inventory + 100}
// REFACTOR THE CLOSURE:

let unlockTreasureChest = {(inventory: inout Int) in inventory += 100}

unlockTreasureChest(&goldBars)

print(goldBars)
