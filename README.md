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
![alt text](/screenshots/Empty_Form.png "Empty generated form" =288x512)
![alt text](/screenshots/Form_with_input.png "Generated form with input" =288x512)
