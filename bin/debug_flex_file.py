import re

with open("sql.flex", 'r') as f:
    lines = f.readlines();
  
new_lines = []
for line in lines:  
    match = re.search('"(.*)"\s\{\sreturn', line)
    if match:
        print line
        print_string = 'System.out.println(" " + yytext() + " ");'
        line = line.replace('return', '%s return' % print_string)
        print line
    new_lines.append(line)
    
with open("sql.flex", "w") as f:
    f.writelines(new_lines)
    