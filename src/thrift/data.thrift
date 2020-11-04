namespace java thrift.generated

typedef i16 short
typedef i32 int
typedef i64 long
typedef bool boolean
typedef string String

struct Person {
    1: optional String username,
    2: optional int age,
    3: optional boolean married
}

exception DataException {
    1: optional string message,
    2: optional string callStack,
    3: optional string date;
}

service PersonService {
    Person findPerson(1: required String username) throws (1: DataException dataException),

    void savePersion(1: required Person person) throws (1: DataException dataException)
}