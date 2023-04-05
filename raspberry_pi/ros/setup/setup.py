from setuptools import setup

package_name = 'boeing_py'

setup(
    name=package_name,
    version='0.0.0',
    packages=[package_name],
    data_files=[
        ('share/ament_index/resource_index/packages',
            ['resource/' + package_name]),
        ('share/' + package_name, ['package.xml']),
    ],
    install_requires=['setuptools'],
    zip_safe=True,
    maintainer='ubuntu',
    maintainer_email='ubuntu@todo.todo',
    description='TODO: Package description',
    license='TODO: License declaration',
    tests_require=['pytest'],
    entry_points={
        'console_scripts': [
        	'server = boeing_py.publisher:main',
        	'drive = boeing_py.drive_sub:main',
        	'grab = boeing_py.grab_sub:main',
        	'cam = boeing_py.cam:main',
        	'metal detector = boeing_py.metalD:main',
        ],
    },
)
