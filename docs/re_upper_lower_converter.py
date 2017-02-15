#Lazy and didn't feel like doing this by hand

with open('upper_lower.txt') as f:
    lines = f.readlines()
    
new_lines = []
for line in lines:
    print line
    new_line = ''
    for char in line.strip():
        if char and char.lower() != char.upper():
            new_line = new_line + ''.join(['[', char.lower(), char.upper(), ']'])
        else:
            new_line = new_line + char
    new_lines.append(new_line)
    print new_line + '\n'
    
with open('upper_lower_out.txt', 'w') as f:
    f.write('\n'.join(new_lines))
        