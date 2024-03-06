//
//  ContentView.swift
//  Reservations
//
//  Created by Luis F. Martínez on 28/10/23.
//

import SwiftUI

struct ContentView: View {
    @State var personCount: Int = 1
    
    var body: some View {
        VStack {
            Text("Little Lemon")
            Stepper {
                Text("Reservation for: \(personCount)")
            } onIncrement: {
                personCount += 1
            } onDecrement: {
                personCount = (personCount == 1) ? 1 : personCount - 1
            }
        }
        .padding()
    }
}

#Preview {
    ContentView(personCount: 10)
}

// same as:
struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView(personCount: 10)
    }
}
