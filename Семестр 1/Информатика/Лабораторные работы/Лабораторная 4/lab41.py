import json
import yaml

f = open("lab4.json", "r", encoding="utf-8")
data = json.load(f)
yaml_data = yaml.dump(data, allow_unicode=True)
open("lab41.yml", "w", encoding="utf-8").write(yaml_data)
