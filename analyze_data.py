import pprint
import numpy as np
#import matplotlib.pyplot as plt

ks = range(1, 10)
vertices = range(25, 400, 25)
densities = [0.7, 0.8, 0.9, 1.0]

filepath = 'olddata/'
filetypes = ['TZ', 'Greedy']
filemetas = ['_density', '_vertices', '_k']
filename = 'olddata/TZ_density1.0_vertices200_k1.csv'

def generate_filename(density, vertices, k):
    #for t in filetypes:
    t = 'TZ'
    filename = filepath + t + '_density' + str(density) + '_vertices' + str(vertices) + '_k' + str(k) + '.csv'

    return filename


def load_data_from_file(meta):

    density = meta['density']
    vertices = meta['vertices']
    k = meta['k']

    filename = generate_filename(density, vertices, k)
    datafile = open(filename, 'r')

    headers = datafile.readline()
    # Remove trailing newline, and split string into a list over commas
    headers = headers[0:len(headers)-1].split(',')

    lines = datafile.readlines()
    # Remove trailing newline, and split string into a list over commas
    lines = [ line[0:len(line)-1].split(",") for line in lines]

    mean = average_string_readings(lines)

    data = {}

    for i in range(0, len(headers)):
        data[headers[i]] = mean[i]

    return data


def average_string_readings(data):

    # Convert list entries to floats
    floatlines = []
    for line in data:
        floatlines.append( [float(l) for l in line] )

    mean = np.mean(floatlines, axis=0)

    return mean

measurements = ['weight', 'density', 'degree', 'runtime']

dicts = {}
dicts['weight'] = []
dicts['density'] = []
dicts['degree'] = []
dicts['runtime'] = []

def initialize_results_dicts():
    for k in ks:
        for d in densities:
            for v in vertices:

                for m in measurements:
                    new_dict = {}

                    new_dict['k'] = k
                    new_dict['density'] = d
                    new_dict['vertices'] = v
                    new_dict['values'] = []

                    dicts[m].append(new_dict)

def select_dicts_by_meta(meta):

    density = meta['density']
    vertices = meta['vertices']
    k = meta['k']

    return_dicts = []

    for m in measurements:
        for d in dicts[m]:
            if d['density'] == density and d['vertices'] == vertices and d['k'] == k:
                return_dicts.append(d)

    return return_dicts

def insert_into_dicts(data):
    pass


    #weight_dict['values'].append(data['weight'])
    #density_dict['values'].append(data['density'])
    #degree_dict['values'].append(data['highest degree'])
    #runtime_dict['values'].append(data['runtime'])

if __name__ == '__main__':

    initialize_results_dicts()

    meta1 = {'density': 1.0, 'vertices': 200, 'k': 1}
    print(select_dicts_by_meta(meta1))
    data1 = load_data_from_file(meta1)
    #insert_into_dicts(data1)

    meta2 = {'density': 1.0, 'vertices': 175, 'k': 1}
    data2 = load_data_from_file(meta2)
    #insert_into_dicts(data2)
