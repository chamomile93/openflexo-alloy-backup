open util/boolean

abstract sig Entity {}

sig Subject extends Entity { authenticator : one Authenticator }

sig Authenticator extends Entity  {
	subjects : set Subject
}

sig AuthenticatorPatternInstance {
	subject : one Subject,
	authenticator : one Authenticator,
	authInfo : one Int,
	proofOfIdentity : one Int,
	isAuthenticating : one Bool,
	isChecking : one Bool,
	isAuthenticated : one Bool,
	invariants : set Invariant
}

abstract sig Invariant {
	holds : AuthenticatorPatternInstance -> one Bool
}

fact {
    all api : AuthenticatorPatternInstance |
        api.invariants = Invariant
}

one sig AuthIsFinal extends Invariant {}

fact {
    AuthIsFinal.holds = AuthenticatorPatternInstance -> True
}

//one sig FalseRelation extends Invariant {}

//fact {
//    FalseRelation.holds = AuthenticatorPatternInstance -> False
//}

//one sig AuthInfoUniqueness extends Invariant {}

one sig AuthInfoIsFinal extends Invariant {}

fact {
    AuthInfoIsFinal.holds = AuthenticatorPatternInstance -> True
}


//one sig IdProofIsValid extends Invariant {}

assert AllHold {
    all api : AuthenticatorPatternInstance |
        all inv : api.invariants |
            inv.holds[api] = True
}

check AllHold

pred allInvariantsHold {
    all api : AuthenticatorPatternInstance |
        all inv : api.invariants |
            inv.holds[api] = True
}

run { allInvariantsHold }
