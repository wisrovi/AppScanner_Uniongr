from flask import Flask
from flask import jsonify

app = Flask(__name__)

data_response = [
    {
        "ruta" : "https://uniongr-my.sharepoint.com/:b:/p/misanchez/EYlTvWOJfMtMi7ICAEriMUsBlHE79s4MznBXrHcka5y9tA?e=zgQEG2",
        "archivo" : "pdf1",
        "id" : 1
    },
    {
        "ruta" : "https://uniongr-my.sharepoint.com/:b:/p/misanchez/EaRy8NN9gjBLvNCfyvwSNj0BI4i7T7epY970olkcwvbPTw?e=bLnfiv",
        "archivo" : "pdf2",
        "id" : 2
    },{
        "ruta" : "https://uniongr-my.sharepoint.com/:b:/p/misanchez/EWHnAJ7Gp_ZHjuHpiHh78_QBoVisX7ybNMvjwD1pF27Onw?e=1vzuIy",
        "archivo" : "pdf3",
        "id" : 3
    },{
        "ruta" : "https://uniongr-my.sharepoint.com/:b:/p/misanchez/EYjCWjZ8CjxAnpUpGKc4DZQB2zyfBM6A8eYxtjnJs3J5nA?e=m8npuX",
        "archivo" : "pdf4",
        "id" : 4
    },{
        "ruta" : "https://uniongr-my.sharepoint.com/:x:/p/misanchez/EX6lpZJ7kNJOgCUcCqTMP14BaKmcXckO1CpTzfBvzHEisw?e=FvJy7f",
        "archivo" : "excel1",
        "id" : 5
    },{
        "ruta" : "https://uniongr-my.sharepoint.com/:x:/p/misanchez/EX6lpZJ7kNJOgCUcCqTMP14BaKmcXckO1CpTzfBvzHEisw?e=FvJy7f",
        "archivo" : "excel2",
        "id" : 6
    },{
        "ruta" : "https://uniongr-my.sharepoint.com/:x:/p/misanchez/EX6lpZJ7kNJOgCUcCqTMP14BaKmcXckO1CpTzfBvzHEisw?e=FvJy7f",
        "archivo" : "excel3",
        "id" : 7
    },{
        "ruta" : "https://uniongr-my.sharepoint.com/:x:/p/misanchez/EX6lpZJ7kNJOgCUcCqTMP14BaKmcXckO1CpTzfBvzHEisw?e=FvJy7f",
        "archivo" : "excel4",
        "id" : 8
    }
]



@app.route('/ruta/<name>')
def hello(name):
    global data_response
    #data_response['radicado'] = name
    return jsonify(data_response)


if __name__ == '__main__':
    app.run(host="0.0.0.0", port=5000)