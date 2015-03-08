Android Example - Generating a form, which is configured by using a JSON-formatted string
===============

The form also has a simple built-in validation check on each text field.


Input:
----------
JSON String which configures the UI layout of the form
`[
    {
        "FIRST_NAME": {
            "FirstName": "string"
        }
    },
    {
        "BIRTHDAY": {
            "Birthday": "date"
        }
    },
    {
        "MOBILE_NUMBER": {
            "MobileNumber": "contact"
        }
    }
]`

Output:
---------
![alt text](/Screenshots/Empty_Form.png "Empty generated form")
![alt text](/Screenshots/Form_with_input.png "Generated form with input")
![alt text](/Screenshots/Form_submitted_correct.png "Form submitted with valid input data")
![alt text](/Screenshots/Form_submitted_incorrect.png "Form submitted with incorrect data")
