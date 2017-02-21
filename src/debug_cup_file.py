import re

with open("sql.cup", 'r') as f:
    lines = f.readlines();
  
new_lines = []
replace = ''
for line in lines:
    match = re.match('([a-zA-Z_]+)\s+::=', line)
    if match:
        replace = match.group(1)
    match = re.search('\s+\{:\sRESULT\s=\strue;\s:};', line)
    if match and replace:
        print line
        new_code = 'System.out.print(" %s ");' % replace
        line = line.replace('RESULT', '%s RESULT' % new_code)
        replace = ''
        print line
    new_lines.append(line)
   
with open("sql.cup", "w") as f:
    f.writelines(new_lines)